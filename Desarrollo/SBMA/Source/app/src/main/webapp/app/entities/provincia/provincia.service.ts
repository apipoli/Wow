import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Provincia } from './provincia.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ProvinciaService {

    private resourceUrl = SERVER_API_URL + 'api/provincias';

    constructor(private http: Http) { }

    create(provincia: Provincia): Observable<Provincia> {
        const copy = this.convert(provincia);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(provincia: Provincia): Observable<Provincia> {
        const copy = this.convert(provincia);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Provincia> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Provincia.
     */
    private convertItemFromServer(json: any): Provincia {
        const entity: Provincia = Object.assign(new Provincia(), json);
        return entity;
    }

    /**
     * Convert a Provincia to a JSON which can be sent to the server.
     */
    private convert(provincia: Provincia): Provincia {
        const copy: Provincia = Object.assign({}, provincia);
        return copy;
    }
}

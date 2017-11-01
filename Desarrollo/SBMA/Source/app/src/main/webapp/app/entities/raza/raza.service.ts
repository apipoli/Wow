import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Raza } from './raza.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RazaService {

    private resourceUrl = SERVER_API_URL + 'api/razas';

    constructor(private http: Http) { }

    create(raza: Raza): Observable<Raza> {
        const copy = this.convert(raza);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(raza: Raza): Observable<Raza> {
        const copy = this.convert(raza);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Raza> {
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
     * Convert a returned JSON object to Raza.
     */
    private convertItemFromServer(json: any): Raza {
        const entity: Raza = Object.assign(new Raza(), json);
        return entity;
    }

    /**
     * Convert a Raza to a JSON which can be sent to the server.
     */
    private convert(raza: Raza): Raza {
        const copy: Raza = Object.assign({}, raza);
        return copy;
    }
}

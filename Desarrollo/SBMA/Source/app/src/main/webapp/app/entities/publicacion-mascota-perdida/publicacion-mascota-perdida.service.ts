import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { PublicacionMascotaPerdida } from './publicacion-mascota-perdida.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PublicacionMascotaPerdidaService {

    private resourceUrl = SERVER_API_URL + 'api/publicacion-mascota-perdidas';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(publicacionMascotaPerdida: PublicacionMascotaPerdida): Observable<PublicacionMascotaPerdida> {
        const copy = this.convert(publicacionMascotaPerdida);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(publicacionMascotaPerdida: PublicacionMascotaPerdida): Observable<PublicacionMascotaPerdida> {
        const copy = this.convert(publicacionMascotaPerdida);
        console.log(copy);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<PublicacionMascotaPerdida> {
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
     * Convert a returned JSON object to PublicacionMascotaPerdida.
     */
    private convertItemFromServer(json: any): PublicacionMascotaPerdida {
        const entity: PublicacionMascotaPerdida = Object.assign(new PublicacionMascotaPerdida(), json);
        entity.fecha = this.dateUtils
            .convertDateTimeFromServer(json.fecha);
        entity.fechaEncuentro = this.dateUtils
            .convertDateTimeFromServer(json.fechaEncuentro);
        return entity;
    }

    /**
     * Convert a PublicacionMascotaPerdida to a JSON which can be sent to the server.
     */
    private convert(publicacionMascotaPerdida: PublicacionMascotaPerdida): PublicacionMascotaPerdida {
        const copy: PublicacionMascotaPerdida = Object.assign({}, publicacionMascotaPerdida);

        copy.fecha = this.dateUtils.toDate(publicacionMascotaPerdida.fecha);

        copy.fechaEncuentro = this.dateUtils.toDate(publicacionMascotaPerdida.fechaEncuentro);
        return copy;
    }
}

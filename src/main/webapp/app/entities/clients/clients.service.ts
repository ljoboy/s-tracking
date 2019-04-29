import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IClients } from 'app/shared/model/clients.model';

type EntityResponseType = HttpResponse<IClients>;
type EntityArrayResponseType = HttpResponse<IClients[]>;

@Injectable({ providedIn: 'root' })
export class ClientsService {
    private resourceUrl = SERVER_API_URL + 'api/clients';

    constructor(private http: HttpClient) {}

    create(clients: IClients): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(clients);
        return this.http
            .post<IClients>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    update(clients: IClients): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(clients);
        return this.http
            .put<IClients>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IClients>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IClients[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(clients: IClients): IClients {
        const copy: IClients = Object.assign({}, clients, {
            dateAbonnement: clients.dateAbonnement != null && clients.dateAbonnement.isValid() ? clients.dateAbonnement.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.dateAbonnement = res.body.dateAbonnement != null ? moment(res.body.dateAbonnement) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((clients: IClients) => {
            clients.dateAbonnement = clients.dateAbonnement != null ? moment(clients.dateAbonnement) : null;
        });
        return res;
    }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IHistoriques } from 'app/shared/model/historiques.model';

type EntityResponseType = HttpResponse<IHistoriques>;
type EntityArrayResponseType = HttpResponse<IHistoriques[]>;

@Injectable({ providedIn: 'root' })
export class HistoriquesService {
    private resourceUrl = SERVER_API_URL + 'api/historiques';

    constructor(private http: HttpClient) {}

    create(historiques: IHistoriques): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(historiques);
        return this.http
            .post<IHistoriques>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    update(historiques: IHistoriques): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(historiques);
        return this.http
            .put<IHistoriques>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IHistoriques>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IHistoriques[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(historiques: IHistoriques): IHistoriques {
        const copy: IHistoriques = Object.assign({}, historiques, {
            dateHistorique:
                historiques.dateHistorique != null && historiques.dateHistorique.isValid() ? historiques.dateHistorique.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.dateHistorique = res.body.dateHistorique != null ? moment(res.body.dateHistorique) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((historiques: IHistoriques) => {
            historiques.dateHistorique = historiques.dateHistorique != null ? moment(historiques.dateHistorique) : null;
        });
        return res;
    }
}

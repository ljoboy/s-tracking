import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IVoitures } from 'app/shared/model/voitures.model';

type EntityResponseType = HttpResponse<IVoitures>;
type EntityArrayResponseType = HttpResponse<IVoitures[]>;

@Injectable({ providedIn: 'root' })
export class VoituresService {
    private resourceUrl = SERVER_API_URL + 'api/voitures';

    constructor(private http: HttpClient) {}

    create(voitures: IVoitures): Observable<EntityResponseType> {
        return this.http.post<IVoitures>(this.resourceUrl, voitures, { observe: 'response' });
    }

    update(voitures: IVoitures): Observable<EntityResponseType> {
        return this.http.put<IVoitures>(this.resourceUrl, voitures, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IVoitures>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IVoitures[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

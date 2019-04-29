import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Historiques } from 'app/shared/model/historiques.model';
import { HistoriquesService } from './historiques.service';
import { HistoriquesComponent } from './historiques.component';
import { HistoriquesDetailComponent } from './historiques-detail.component';
import { HistoriquesUpdateComponent } from './historiques-update.component';
import { HistoriquesDeletePopupComponent } from './historiques-delete-dialog.component';
import { IHistoriques } from 'app/shared/model/historiques.model';

@Injectable({ providedIn: 'root' })
export class HistoriquesResolve implements Resolve<IHistoriques> {
    constructor(private service: HistoriquesService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((historiques: HttpResponse<Historiques>) => historiques.body);
        }
        return Observable.of(new Historiques());
    }
}

export const historiquesRoute: Routes = [
    {
        path: 'historiques',
        component: HistoriquesComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'sTrackingApp.historiques.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'historiques/:id/view',
        component: HistoriquesDetailComponent,
        resolve: {
            historiques: HistoriquesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sTrackingApp.historiques.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'historiques/new',
        component: HistoriquesUpdateComponent,
        resolve: {
            historiques: HistoriquesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sTrackingApp.historiques.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'historiques/:id/edit',
        component: HistoriquesUpdateComponent,
        resolve: {
            historiques: HistoriquesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sTrackingApp.historiques.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const historiquesPopupRoute: Routes = [
    {
        path: 'historiques/:id/delete',
        component: HistoriquesDeletePopupComponent,
        resolve: {
            historiques: HistoriquesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sTrackingApp.historiques.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

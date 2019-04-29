import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Voitures } from 'app/shared/model/voitures.model';
import { VoituresService } from './voitures.service';
import { VoituresComponent } from './voitures.component';
import { VoituresDetailComponent } from './voitures-detail.component';
import { VoituresUpdateComponent } from './voitures-update.component';
import { VoituresDeletePopupComponent } from './voitures-delete-dialog.component';
import { IVoitures } from 'app/shared/model/voitures.model';

@Injectable({ providedIn: 'root' })
export class VoituresResolve implements Resolve<IVoitures> {
    constructor(private service: VoituresService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((voitures: HttpResponse<Voitures>) => voitures.body);
        }
        return Observable.of(new Voitures());
    }
}

export const voituresRoute: Routes = [
    {
        path: 'voitures',
        component: VoituresComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'sTrackingApp.voitures.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'voitures/:id/view',
        component: VoituresDetailComponent,
        resolve: {
            voitures: VoituresResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sTrackingApp.voitures.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'voitures/new',
        component: VoituresUpdateComponent,
        resolve: {
            voitures: VoituresResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sTrackingApp.voitures.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'voitures/:id/edit',
        component: VoituresUpdateComponent,
        resolve: {
            voitures: VoituresResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sTrackingApp.voitures.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const voituresPopupRoute: Routes = [
    {
        path: 'voitures/:id/delete',
        component: VoituresDeletePopupComponent,
        resolve: {
            voitures: VoituresResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sTrackingApp.voitures.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

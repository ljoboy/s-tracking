import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Clients } from 'app/shared/model/clients.model';
import { ClientsService } from './clients.service';
import { ClientsComponent } from './clients.component';
import { ClientsDetailComponent } from './clients-detail.component';
import { ClientsUpdateComponent } from './clients-update.component';
import { ClientsDeletePopupComponent } from './clients-delete-dialog.component';
import { IClients } from 'app/shared/model/clients.model';

@Injectable({ providedIn: 'root' })
export class ClientsResolve implements Resolve<IClients> {
    constructor(private service: ClientsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((clients: HttpResponse<Clients>) => clients.body);
        }
        return Observable.of(new Clients());
    }
}

export const clientsRoute: Routes = [
    {
        path: 'clients',
        component: ClientsComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'sTrackingApp.clients.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'clients/:id/view',
        component: ClientsDetailComponent,
        resolve: {
            clients: ClientsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sTrackingApp.clients.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'clients/new',
        component: ClientsUpdateComponent,
        resolve: {
            clients: ClientsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sTrackingApp.clients.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'clients/:id/edit',
        component: ClientsUpdateComponent,
        resolve: {
            clients: ClientsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sTrackingApp.clients.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const clientsPopupRoute: Routes = [
    {
        path: 'clients/:id/delete',
        component: ClientsDeletePopupComponent,
        resolve: {
            clients: ClientsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sTrackingApp.clients.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

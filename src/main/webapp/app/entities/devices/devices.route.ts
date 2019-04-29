import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Devices } from 'app/shared/model/devices.model';
import { DevicesService } from './devices.service';
import { DevicesComponent } from './devices.component';
import { DevicesDetailComponent } from './devices-detail.component';
import { DevicesUpdateComponent } from './devices-update.component';
import { DevicesDeletePopupComponent } from './devices-delete-dialog.component';
import { IDevices } from 'app/shared/model/devices.model';

@Injectable({ providedIn: 'root' })
export class DevicesResolve implements Resolve<IDevices> {
    constructor(private service: DevicesService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((devices: HttpResponse<Devices>) => devices.body);
        }
        return Observable.of(new Devices());
    }
}

export const devicesRoute: Routes = [
    {
        path: 'devices',
        component: DevicesComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'sTrackingApp.devices.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'devices/:id/view',
        component: DevicesDetailComponent,
        resolve: {
            devices: DevicesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sTrackingApp.devices.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'devices/new',
        component: DevicesUpdateComponent,
        resolve: {
            devices: DevicesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sTrackingApp.devices.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'devices/:id/edit',
        component: DevicesUpdateComponent,
        resolve: {
            devices: DevicesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sTrackingApp.devices.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const devicesPopupRoute: Routes = [
    {
        path: 'devices/:id/delete',
        component: DevicesDeletePopupComponent,
        resolve: {
            devices: DevicesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sTrackingApp.devices.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

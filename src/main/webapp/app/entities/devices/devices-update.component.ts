import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IDevices } from 'app/shared/model/devices.model';
import { DevicesService } from './devices.service';
import { IClients } from 'app/shared/model/clients.model';
import { ClientsService } from 'app/entities/clients';

@Component({
    selector: 'jhi-devices-update',
    templateUrl: './devices-update.component.html'
})
export class DevicesUpdateComponent implements OnInit {
    private _devices: IDevices;
    isSaving: boolean;

    clients: IClients[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private devicesService: DevicesService,
        private clientsService: ClientsService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ devices }) => {
            this.devices = devices;
        });
        this.clientsService.query().subscribe(
            (res: HttpResponse<IClients[]>) => {
                this.clients = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.devices.id !== undefined) {
            this.subscribeToSaveResponse(this.devicesService.update(this.devices));
        } else {
            this.subscribeToSaveResponse(this.devicesService.create(this.devices));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDevices>>) {
        result.subscribe((res: HttpResponse<IDevices>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackClientsById(index: number, item: IClients) {
        return item.id;
    }
    get devices() {
        return this._devices;
    }

    set devices(devices: IDevices) {
        this._devices = devices;
    }
}

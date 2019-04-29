import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IVoitures } from 'app/shared/model/voitures.model';
import { VoituresService } from './voitures.service';
import { IDevices } from 'app/shared/model/devices.model';
import { DevicesService } from 'app/entities/devices';

@Component({
    selector: 'jhi-voitures-update',
    templateUrl: './voitures-update.component.html'
})
export class VoituresUpdateComponent implements OnInit {
    private _voitures: IVoitures;
    isSaving: boolean;

    devices: IDevices[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private voituresService: VoituresService,
        private devicesService: DevicesService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ voitures }) => {
            this.voitures = voitures;
        });
        this.devicesService.query({ filter: 'voitures-is-null' }).subscribe(
            (res: HttpResponse<IDevices[]>) => {
                if (!this.voitures.devices || !this.voitures.devices.id) {
                    this.devices = res.body;
                } else {
                    this.devicesService.find(this.voitures.devices.id).subscribe(
                        (subRes: HttpResponse<IDevices>) => {
                            this.devices = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.voitures.id !== undefined) {
            this.subscribeToSaveResponse(this.voituresService.update(this.voitures));
        } else {
            this.subscribeToSaveResponse(this.voituresService.create(this.voitures));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IVoitures>>) {
        result.subscribe((res: HttpResponse<IVoitures>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackDevicesById(index: number, item: IDevices) {
        return item.id;
    }
    get voitures() {
        return this._voitures;
    }

    set voitures(voitures: IVoitures) {
        this._voitures = voitures;
    }
}

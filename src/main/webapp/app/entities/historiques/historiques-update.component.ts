import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IHistoriques } from 'app/shared/model/historiques.model';
import { HistoriquesService } from './historiques.service';
import { IDevices } from 'app/shared/model/devices.model';
import { DevicesService } from 'app/entities/devices';

@Component({
    selector: 'jhi-historiques-update',
    templateUrl: './historiques-update.component.html'
})
export class HistoriquesUpdateComponent implements OnInit {
    private _historiques: IHistoriques;
    isSaving: boolean;

    devices: IDevices[];
    dateHistorique: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private historiquesService: HistoriquesService,
        private devicesService: DevicesService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ historiques }) => {
            this.historiques = historiques;
        });
        this.devicesService.query().subscribe(
            (res: HttpResponse<IDevices[]>) => {
                this.devices = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.historiques.dateHistorique = moment(this.dateHistorique, DATE_TIME_FORMAT);
        if (this.historiques.id !== undefined) {
            this.subscribeToSaveResponse(this.historiquesService.update(this.historiques));
        } else {
            this.subscribeToSaveResponse(this.historiquesService.create(this.historiques));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IHistoriques>>) {
        result.subscribe((res: HttpResponse<IHistoriques>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get historiques() {
        return this._historiques;
    }

    set historiques(historiques: IHistoriques) {
        this._historiques = historiques;
        this.dateHistorique = moment(historiques.dateHistorique).format(DATE_TIME_FORMAT);
    }
}

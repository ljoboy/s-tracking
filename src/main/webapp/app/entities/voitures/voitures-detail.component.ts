import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVoitures } from 'app/shared/model/voitures.model';

@Component({
    selector: 'jhi-voitures-detail',
    templateUrl: './voitures-detail.component.html'
})
export class VoituresDetailComponent implements OnInit {
    voitures: IVoitures;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ voitures }) => {
            this.voitures = voitures;
        });
    }

    previousState() {
        window.history.back();
    }
}

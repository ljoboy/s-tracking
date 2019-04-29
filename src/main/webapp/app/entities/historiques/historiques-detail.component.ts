import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHistoriques } from 'app/shared/model/historiques.model';

@Component({
    selector: 'jhi-historiques-detail',
    templateUrl: './historiques-detail.component.html'
})
export class HistoriquesDetailComponent implements OnInit {
    historiques: IHistoriques;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ historiques }) => {
            this.historiques = historiques;
        });
    }

    previousState() {
        window.history.back();
    }
}

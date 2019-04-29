import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClients } from 'app/shared/model/clients.model';

@Component({
    selector: 'jhi-clients-detail',
    templateUrl: './clients-detail.component.html'
})
export class ClientsDetailComponent implements OnInit {
    clients: IClients;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ clients }) => {
            this.clients = clients;
        });
    }

    previousState() {
        window.history.back();
    }
}

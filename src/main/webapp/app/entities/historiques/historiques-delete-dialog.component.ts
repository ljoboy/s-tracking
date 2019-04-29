import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHistoriques } from 'app/shared/model/historiques.model';
import { HistoriquesService } from './historiques.service';

@Component({
    selector: 'jhi-historiques-delete-dialog',
    templateUrl: './historiques-delete-dialog.component.html'
})
export class HistoriquesDeleteDialogComponent {
    historiques: IHistoriques;

    constructor(
        private historiquesService: HistoriquesService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.historiquesService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'historiquesListModification',
                content: 'Deleted an historiques'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-historiques-delete-popup',
    template: ''
})
export class HistoriquesDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ historiques }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(HistoriquesDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.historiques = historiques;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}

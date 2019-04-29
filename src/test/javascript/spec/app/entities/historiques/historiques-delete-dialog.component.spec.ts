/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { STrackingTestModule } from '../../../test.module';
import { HistoriquesDeleteDialogComponent } from 'app/entities/historiques/historiques-delete-dialog.component';
import { HistoriquesService } from 'app/entities/historiques/historiques.service';

describe('Component Tests', () => {
    describe('Historiques Management Delete Component', () => {
        let comp: HistoriquesDeleteDialogComponent;
        let fixture: ComponentFixture<HistoriquesDeleteDialogComponent>;
        let service: HistoriquesService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [STrackingTestModule],
                declarations: [HistoriquesDeleteDialogComponent]
            })
                .overrideTemplate(HistoriquesDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(HistoriquesDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HistoriquesService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});

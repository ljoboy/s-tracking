/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { STrackingTestModule } from '../../../test.module';
import { VoituresDeleteDialogComponent } from 'app/entities/voitures/voitures-delete-dialog.component';
import { VoituresService } from 'app/entities/voitures/voitures.service';

describe('Component Tests', () => {
    describe('Voitures Management Delete Component', () => {
        let comp: VoituresDeleteDialogComponent;
        let fixture: ComponentFixture<VoituresDeleteDialogComponent>;
        let service: VoituresService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [STrackingTestModule],
                declarations: [VoituresDeleteDialogComponent]
            })
                .overrideTemplate(VoituresDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(VoituresDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VoituresService);
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

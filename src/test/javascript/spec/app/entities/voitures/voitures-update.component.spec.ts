/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { STrackingTestModule } from '../../../test.module';
import { VoituresUpdateComponent } from 'app/entities/voitures/voitures-update.component';
import { VoituresService } from 'app/entities/voitures/voitures.service';
import { Voitures } from 'app/shared/model/voitures.model';

describe('Component Tests', () => {
    describe('Voitures Management Update Component', () => {
        let comp: VoituresUpdateComponent;
        let fixture: ComponentFixture<VoituresUpdateComponent>;
        let service: VoituresService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [STrackingTestModule],
                declarations: [VoituresUpdateComponent]
            })
                .overrideTemplate(VoituresUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(VoituresUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VoituresService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Voitures(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.voitures = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Voitures();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.voitures = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { STrackingTestModule } from '../../../test.module';
import { HistoriquesUpdateComponent } from 'app/entities/historiques/historiques-update.component';
import { HistoriquesService } from 'app/entities/historiques/historiques.service';
import { Historiques } from 'app/shared/model/historiques.model';

describe('Component Tests', () => {
    describe('Historiques Management Update Component', () => {
        let comp: HistoriquesUpdateComponent;
        let fixture: ComponentFixture<HistoriquesUpdateComponent>;
        let service: HistoriquesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [STrackingTestModule],
                declarations: [HistoriquesUpdateComponent]
            })
                .overrideTemplate(HistoriquesUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(HistoriquesUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HistoriquesService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Historiques(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.historiques = entity;
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
                    const entity = new Historiques();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.historiques = entity;
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

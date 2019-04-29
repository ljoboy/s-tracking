/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { STrackingTestModule } from '../../../test.module';
import { HistoriquesDetailComponent } from 'app/entities/historiques/historiques-detail.component';
import { Historiques } from 'app/shared/model/historiques.model';

describe('Component Tests', () => {
    describe('Historiques Management Detail Component', () => {
        let comp: HistoriquesDetailComponent;
        let fixture: ComponentFixture<HistoriquesDetailComponent>;
        const route = ({ data: of({ historiques: new Historiques(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [STrackingTestModule],
                declarations: [HistoriquesDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(HistoriquesDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(HistoriquesDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.historiques).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

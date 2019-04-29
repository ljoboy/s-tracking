/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { STrackingTestModule } from '../../../test.module';
import { VoituresDetailComponent } from 'app/entities/voitures/voitures-detail.component';
import { Voitures } from 'app/shared/model/voitures.model';

describe('Component Tests', () => {
    describe('Voitures Management Detail Component', () => {
        let comp: VoituresDetailComponent;
        let fixture: ComponentFixture<VoituresDetailComponent>;
        const route = ({ data: of({ voitures: new Voitures(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [STrackingTestModule],
                declarations: [VoituresDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(VoituresDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(VoituresDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.voitures).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

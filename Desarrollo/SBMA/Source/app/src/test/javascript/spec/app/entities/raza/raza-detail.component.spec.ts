/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AppTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RazaDetailComponent } from '../../../../../../main/webapp/app/entities/raza/raza-detail.component';
import { RazaService } from '../../../../../../main/webapp/app/entities/raza/raza.service';
import { Raza } from '../../../../../../main/webapp/app/entities/raza/raza.model';

describe('Component Tests', () => {

    describe('Raza Management Detail Component', () => {
        let comp: RazaDetailComponent;
        let fixture: ComponentFixture<RazaDetailComponent>;
        let service: RazaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AppTestModule],
                declarations: [RazaDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RazaService,
                    JhiEventManager
                ]
            }).overrideTemplate(RazaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RazaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RazaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Raza(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.raza).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});

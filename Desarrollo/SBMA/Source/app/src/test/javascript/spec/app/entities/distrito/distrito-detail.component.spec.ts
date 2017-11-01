/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AppTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { DistritoDetailComponent } from '../../../../../../main/webapp/app/entities/distrito/distrito-detail.component';
import { DistritoService } from '../../../../../../main/webapp/app/entities/distrito/distrito.service';
import { Distrito } from '../../../../../../main/webapp/app/entities/distrito/distrito.model';

describe('Component Tests', () => {

    describe('Distrito Management Detail Component', () => {
        let comp: DistritoDetailComponent;
        let fixture: ComponentFixture<DistritoDetailComponent>;
        let service: DistritoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AppTestModule],
                declarations: [DistritoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    DistritoService,
                    JhiEventManager
                ]
            }).overrideTemplate(DistritoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DistritoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DistritoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Distrito(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.distrito).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AppTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ProvinciaDetailComponent } from '../../../../../../main/webapp/app/entities/provincia/provincia-detail.component';
import { ProvinciaService } from '../../../../../../main/webapp/app/entities/provincia/provincia.service';
import { Provincia } from '../../../../../../main/webapp/app/entities/provincia/provincia.model';

describe('Component Tests', () => {

    describe('Provincia Management Detail Component', () => {
        let comp: ProvinciaDetailComponent;
        let fixture: ComponentFixture<ProvinciaDetailComponent>;
        let service: ProvinciaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AppTestModule],
                declarations: [ProvinciaDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ProvinciaService,
                    JhiEventManager
                ]
            }).overrideTemplate(ProvinciaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProvinciaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProvinciaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Provincia(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.provincia).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AppTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MascotaDetailComponent } from '../../../../../../main/webapp/app/entities/mascota/mascota-detail.component';
import { MascotaService } from '../../../../../../main/webapp/app/entities/mascota/mascota.service';
import { Mascota } from '../../../../../../main/webapp/app/entities/mascota/mascota.model';

describe('Component Tests', () => {

    describe('Mascota Management Detail Component', () => {
        let comp: MascotaDetailComponent;
        let fixture: ComponentFixture<MascotaDetailComponent>;
        let service: MascotaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AppTestModule],
                declarations: [MascotaDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MascotaService,
                    JhiEventManager
                ]
            }).overrideTemplate(MascotaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MascotaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MascotaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Mascota(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.mascota).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});

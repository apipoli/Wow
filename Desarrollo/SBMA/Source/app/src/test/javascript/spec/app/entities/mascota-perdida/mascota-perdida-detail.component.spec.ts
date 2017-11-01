/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AppTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MascotaPerdidaDetailComponent } from '../../../../../../main/webapp/app/entities/mascota-perdida/mascota-perdida-detail.component';
import { MascotaPerdidaService } from '../../../../../../main/webapp/app/entities/mascota-perdida/mascota-perdida.service';
import { MascotaPerdida } from '../../../../../../main/webapp/app/entities/mascota-perdida/mascota-perdida.model';

describe('Component Tests', () => {

    describe('MascotaPerdida Management Detail Component', () => {
        let comp: MascotaPerdidaDetailComponent;
        let fixture: ComponentFixture<MascotaPerdidaDetailComponent>;
        let service: MascotaPerdidaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AppTestModule],
                declarations: [MascotaPerdidaDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MascotaPerdidaService,
                    JhiEventManager
                ]
            }).overrideTemplate(MascotaPerdidaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MascotaPerdidaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MascotaPerdidaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MascotaPerdida(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.mascotaPerdida).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});

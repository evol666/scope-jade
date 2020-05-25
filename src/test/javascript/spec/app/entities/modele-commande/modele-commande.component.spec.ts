import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JadeTestModule } from '../../../test.module';
import { ModeleCommandeComponent } from 'app/entities/modele-commande/modele-commande.component';
import { ModeleCommandeService } from 'app/entities/modele-commande/modele-commande.service';
import { ModeleCommande } from 'app/shared/model/modele-commande.model';

describe('Component Tests', () => {
  describe('ModeleCommande Management Component', () => {
    let comp: ModeleCommandeComponent;
    let fixture: ComponentFixture<ModeleCommandeComponent>;
    let service: ModeleCommandeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JadeTestModule],
        declarations: [ModeleCommandeComponent],
      })
        .overrideTemplate(ModeleCommandeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ModeleCommandeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ModeleCommandeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ModeleCommande(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.modeleCommandes && comp.modeleCommandes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

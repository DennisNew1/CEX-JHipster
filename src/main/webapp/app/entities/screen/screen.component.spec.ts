/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Screen from './screen.vue';
import ScreenService from './screen.service';
import AlertService from '@/shared/alert/alert.service';

type ScreenComponentType = InstanceType<typeof Screen>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Screen Management Component', () => {
    let screenServiceStub: SinonStubbedInstance<ScreenService>;
    let mountOptions: MountingOptions<ScreenComponentType>['global'];

    beforeEach(() => {
      screenServiceStub = sinon.createStubInstance<ScreenService>(ScreenService);
      screenServiceStub.retrieve.resolves({ headers: {} });

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          bModal: bModalStub as any,
          'font-awesome-icon': true,
          'b-badge': true,
          'b-button': true,
          'router-link': true,
        },
        directives: {
          'b-modal': {},
        },
        provide: {
          alertService,
          screenService: () => screenServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        screenServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(Screen, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(screenServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.screens[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: ScreenComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Screen, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        screenServiceStub.retrieve.reset();
        screenServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        screenServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeScreen();
        await comp.$nextTick(); // clear components

        // THEN
        expect(screenServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(screenServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});

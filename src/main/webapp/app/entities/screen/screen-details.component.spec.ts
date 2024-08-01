/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import ScreenDetails from './screen-details.vue';
import ScreenService from './screen.service';
import AlertService from '@/shared/alert/alert.service';

type ScreenDetailsComponentType = InstanceType<typeof ScreenDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const screenSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Screen Management Detail Component', () => {
    let screenServiceStub: SinonStubbedInstance<ScreenService>;
    let mountOptions: MountingOptions<ScreenDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      screenServiceStub = sinon.createStubInstance<ScreenService>(ScreenService);

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'router-link': true,
        },
        provide: {
          alertService,
          screenService: () => screenServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        screenServiceStub.find.resolves(screenSample);
        route = {
          params: {
            screenId: '' + 123,
          },
        };
        const wrapper = shallowMount(ScreenDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.screen).toMatchObject(screenSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        screenServiceStub.find.resolves(screenSample);
        const wrapper = shallowMount(ScreenDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});

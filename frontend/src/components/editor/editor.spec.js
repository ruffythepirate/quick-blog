import { shallowMount } from '@vue/test-utils';
import cut from './editor.vue';

jest.mock('lodash/debounce', () => jest.fn());
const debounceMock = require('lodash/debounce');

const TEXT_AREA_SELECTOR = '#editor textarea';

describe('editor', () => {
  const factory = (values = {}) => shallowMount(
    cut,
    {
      data() {
        return { ...values };
      },
    },
  );

  beforeEach(() => {
  });

  it('renders the input data', () => {
    const wrapper = factory({ input: 'hello world' });

    expect(wrapper.find(TEXT_AREA_SELECTOR).element.value).toEqual('hello world');
  });

  it('calls debounce on text change', () => {
    const wrapper = factory();

    const textarea = wrapper.find(TEXT_AREA_SELECTOR);
    textarea.setValue('new value');

    expect(debounceMock).toHaveBeenCalled();
  });

  it('renders markdown when debounce method is called', () => {

  });
});

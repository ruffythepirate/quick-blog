import { shallowMount } from '@vue/test-utils';
import cut from './MarkdownEditor.vue';

jest.mock('lodash/debounce', () => jest.fn());
const debounceMock = require('lodash/debounce');

const TEXT_AREA_SELECTOR = 'textarea';

describe('editor', () => {
  const factory = () => shallowMount(cut);

  it('renders the input data', () => {
    const wrapper = factory();
    wrapper.setData({value: 'hello world'});

    expect(wrapper.find(TEXT_AREA_SELECTOR).element.value).toEqual('hello world');
  });

  it('calls debounce on text change', () => {
    const wrapper = factory();

    const textarea = wrapper.find(TEXT_AREA_SELECTOR);
    textarea.setValue('new value');

    expect(debounceMock).toHaveBeenCalled();
  });

  it('renders markdown when debounce method is called', () => {
    const wrapper = factory();

    const textarea = wrapper.find(TEXT_AREA_SELECTOR);
    textarea.setValue('new value');

    debounceMock.mock.calls[0][0]({ target: { value: 'new value' } });
    expect(wrapper.find('.compiled-view').text()).toBe('new value');
  });
});

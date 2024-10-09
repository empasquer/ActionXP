import { mount } from '@vue/test-utils';
import CreateActivityForm from '../../components/CreateActivityForm.vue';
import { describe, expect, it, vi } from 'vitest';

describe('CreateActivityForm.vue', () => {
  it('renders the first step of the form', () => {
    const wrapper = mount(CreateActivityForm);

    // Initially, the form should show the first step
    expect(wrapper.find('input[placeholder="fx: Badminton"]').exists()).toBe(true);
  });

  it('proceeds to the second step when "Next" is clicked', async () => {
    const wrapper = mount(CreateActivityForm);
  
    // Simulate filling in the first step and clicking next
    await wrapper.find('input[placeholder="fx: Badminton"]').setValue('My New Activity');
    await wrapper.find('textarea[placeholder="fx: Our badminton court can fit up to 4 people..."').setValue('Our badminton court can fit up to 4 people...');
    await wrapper.find('Basebutton[type="submit"]').trigger('click');

    // Wait for the DOM to update
    await wrapper.vm.$nextTick();

    // Now the second input should appear
    expect(wrapper.find('input[placeholder="fx: 30, 45, 60"]').exists()).toBe(true);
  });

  it('submits the form and shows the success message', async () => {
    const wrapper = mount(CreateActivityForm);

    // Fill out the first step
    await wrapper.find('input[placeholder="fx: Badminton"]').setValue('My New Activity');
    await wrapper.find('textarea').setValue('Our badminton court can fit up to 4 people...');
    await wrapper.find('button[type="submit"]').trigger('click');

    // Wait for the DOM to update
    await wrapper.vm.$nextTick();

    // Check that the second slide is rendered
    expect(wrapper.find('input[placeholder="fx: 30, 45, 60"]').exists()).toBe(true);

    // Fill out the second step
    await wrapper.find('input[placeholder="fx: 30, 45, 60"]').setValue('30, 45');
    await wrapper.find('input[placeholder="fx: https://example.com/image.jpg"]').setValue('https://example.com/image.jpg');

    // Trigger submit for the second slide
    await wrapper.find('button[type="submit"]').trigger('click');

    // Wait for the DOM to update
    await wrapper.vm.$nextTick();

    // Now the success message should appear
    expect(wrapper.find('.success-message h3').text()).toBe('Activity added!');
  });
});

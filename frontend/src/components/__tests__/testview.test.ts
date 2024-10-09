import { mount } from '@vue/test-utils'
import TestHeader from '@/components/TestHeader.vue' // Adjust the path as necessary
import BaseNavigation from '.././BaseNavigation.vue';
import { describe, expect, it } from 'vitest'

describe('TestHeader.vue', () => {
  it('renders the header with navigation items', () => {
    const wrapper = mount(TestHeader)
    
    // Check if the header contains the logo
    expect(wrapper.find('.logo img').exists()).toBe(true)
    
    // Check if the navigation item exists
    expect(wrapper.find('.nav-section').text()).toContain('Activities')
  })

  it('shows the CreateActivityForm when "Add Activity" is selected from the dropdown', async () => {
    const wrapper = mount(TestHeader)

    // Initially, formVisible should be false
    expect(wrapper.vm.formVisible).toBe(false)

    // Simulate the dropdown selection
    await wrapper.findComponent(BaseNavigation).vm.$emit('dropdown-select', 'add-activity')

    // After the selection, formVisible should be true
    expect(wrapper.vm.formVisible).toBe(true)
  })
})

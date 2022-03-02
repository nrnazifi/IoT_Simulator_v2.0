import { html, LitElement, customElement } from 'lit-element';
import '@vaadin/vaadin-ordered-layout/vaadin-horizontal-layout';
import '@vaadin/vaadin-ordered-layout/vaadin-vertical-layout';
import '@vaadin/vaadin-button';
// @ts-ignore
import { applyTheme } from '../../generated/theme';


@customElement('parking-template-view')
export class ParkingTemplateView extends LitElement {
    connectedCallback() {
        super.connectedCallback();
        // Apply the theme manually because of https://github.com/vaadin/flow/issues/11160
        applyTheme(this.renderRoot);
    }

    render() {
        return html`
      <main class="w-full max-w-screen-lg mx-auto pb-l px-l">
        <vaadin-horizontal-layout class="items-center justify-between">
            <vaadin-vertical-layout>
                <h2 class="mb-0 mt-xl text-3xl">Parking Templates</h2>
                <p class="mb-xl mt-0 text-secondary">Your saved templates</p>
            </vaadin-vertical-layout>
            <vaadin-horizontal-layout class="items-center justify-between" theme="padding spacing">
                <vaadin-button theme="primary" id="btnAdd">Add</vaadin-button>
                <vaadin-button theme="success primary" id="btnImport">Import Template</vaadin-button>
            </vaadin-horizontal-layout>
        </vaadin-horizontal-layout>
        <ol class="gap-m grid list-none m-0 p-0">
          <slot></slot>
        </ol>
      </main>
    `;
    }
}

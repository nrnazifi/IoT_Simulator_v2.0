import { html, LitElement, customElement } from 'lit-element';
import '@vaadin/vaadin-ordered-layout/vaadin-horizontal-layout';
import '@vaadin/vaadin-ordered-layout/vaadin-vertical-layout';
import '@vaadin/vaadin-button';
// @ts-ignore
import { applyTheme } from '../../generated/theme';


@customElement('simulation-view')
export class SimulationView extends LitElement {
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
                    <h2 class="mb-0 mt-xl text-3xl">Simulations</h2>
                    <p class="mb-xl mt-0 text-secondary">View the status of running or finished simulation jobs</p>
                </vaadin-vertical-layout>
                <vaadin-horizontal-layout class="items-center justify-between" theme="on spacing-s">
                    <vaadin-button theme="primary" id="btnNew">New</vaadin-button>
                </vaadin-horizontal-layout>
            </vaadin-horizontal-layout>
            <ol id="itemList" class="gap-m grid list-none m-0 p-0">
                
            </ol>
        </main>
    `;
    }
}

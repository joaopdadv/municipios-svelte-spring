import tailwindcss from '@tailwindcss/vite';
import { sveltekit } from '@sveltejs/kit/vite';
import { defineConfig } from 'vite';
import path from 'path';

export default defineConfig({
	plugins: [tailwindcss(), sveltekit()],
	resolve: {
		alias: {
			// ajuste o 'index.js' pro nome correto que você viu no ls
			'svelte-toolbelt': path.resolve(
				__dirname,
				'node_modules/svelte-toolbelt/dist/index.js'
			)
		}
	},
	optimizeDeps: {
		// força o Vite a pré-processar esse pacote
		include: ['svelte-toolbelt']
	}
});

import { redirect, type Actions } from '@sveltejs/kit';

export function load() {
    throw redirect(302, '/dashboard/estados');
}
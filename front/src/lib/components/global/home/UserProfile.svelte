<script lang="ts">
    import { onMount } from "svelte";
    import { writable } from "svelte/store";
    import { browserCookies } from "$lib/services/cookies";
    import type { IUser } from "$lib/types/auth";
    import { goto } from "$app/navigation";
    import Button from "$lib/components/ui/button/button.svelte";

    const user = writable<IUser | null>(null);

    onMount(() => {
        const userCookie = browserCookies.get("user");
        if (userCookie) {
            try {
                const userData: IUser = JSON.parse(userCookie);
                user.set(userData);
            } catch (e) {
                console.error("Failed to parse user cookie:", e);
                user.set(null);
            }
        }
    });

    const handleLogout = () => {
        browserCookies.remove("user");
        user.set(null);
        goto("/home");
    };
</script>

{#if $user}
    <div class="flex items-center gap-4">
        <div class="flex flex-col items-end">
            <p>{$user.email}</p>
            <!-- {#if $user.email}
                <p class="text-sm text-gray-400">{$user.email}</p>
            {/if} -->
        </div>
        <div class="bg-gray-300 h-8 w-8 rounded-full"></div>
        <button
            class="text-sm cursor-pointer font-bold bg-transparent shadow-none text-black hover:bg-transparent"
            on:click={() => handleLogout()}
        >
            Sair
        </button>
    </div>
{:else}
    <a href="/home">Fazer Login</a>
{/if}

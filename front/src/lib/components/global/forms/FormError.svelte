<script lang="ts">
    export let message: string | null | undefined = null;
</script>

{#if message}
    <div class="pt-2">
        <p class="text-sm font-medium text-red-500" role="alert">
            {message}
        </p>
    </div>
{/if}

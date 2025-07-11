<script lang="ts">
    import Menu from "$lib/components/global/home/Menu.svelte";
    import "../../app.css";

    let { children } = $props();
</script>

<div
    class="w-screen h-screen bg-background flex flex-col items-center justify-start p-6 gap-6"
>
    <Menu />
    {@render children()}
</div>

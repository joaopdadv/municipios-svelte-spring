<script lang="ts">
    import "../../../app.css";

    let { children }: { children: any } = $props();
</script>

<div class="flex-1 w-full flex flex-col gap-4 min-h-0">
    <div class="flex-1 w-full min-h-0">
        {@render children()}
    </div>
</div>

<script>
    import Button from "$lib/components/ui/button/button.svelte";
    import UserProfile from "./UserProfile.svelte";
</script>

<div
    class="w-full h-20 bg-white rounded-xl flex items-center justify-between px-4"
>
    <p class="text-xl font-bold text-gray-800">Municipios</p>
    <div class="flex items-center gap-4">
        <UserProfile />
    </div>
</div>

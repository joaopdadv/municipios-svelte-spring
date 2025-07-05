<script lang="ts">
    import type { PageData } from "./$types";
    import DialogManager from "./(components)/DialogManager.svelte";

    let { data }: { data: PageData } = $props();
</script>

<div
    class="flex flex-col items-start justify-center w-full h-screen bg-cover bg-center bg-no-repeat"
    style="background-image: url('/background-home.jpg');"
>
    <div class="absolute inset-0 bg-black/70"></div>

    <div class="relative z-10 flex h-full w-full flex-col items-start">
        <div class="flex w-full items-center justify-between p-4">
            <p class="text-xl font-bold text-white">Municipios</p>

            <DialogManager {data} initialForm="login" />
        </div>

        <div class="flex h-full w-full items-center justify-center">
            <div
                class="flex w-full max-w-md flex-col items-center justify-center p-4"
            >
                <h1 class="mb-4 text-2xl font-bold text-white">
                    Bem-vindo ao Municipios
                </h1>
                <DialogManager {data} initialForm="register" />
            </div>
        </div>
    </div>
</div>

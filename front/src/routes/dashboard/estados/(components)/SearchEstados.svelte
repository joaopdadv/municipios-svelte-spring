<script lang="ts">
    import * as Sheet from "$lib/components/ui/sheet/index.js";
    import { buttonVariants } from "$lib/components/ui/button/index.js";
    import { Input } from "$lib/components/ui/input/index.js";
    import * as Form from "$lib/components/ui/form/index.js";
    import * as Select from "$lib/components/ui/select/index.js";
    import {
        superForm,
        type Infer,
        type SuperValidated,
    } from "sveltekit-superforms";
    import {
        searchEstadosSchema,
        type SearchEstadosSchema,
    } from "../(schema)/searchEstadosSchema";
    import { zodClient } from "sveltekit-superforms/adapters";
    import { toast } from "svelte-sonner";
    import { goto } from "$app/navigation";
    import type { IPage } from "$lib/types/auth";
    import type { IEstado } from "$lib/types/estados";
    import { page } from "$app/stores";
    import { derived } from "svelte/store";
    import Button from "$lib/components/ui/button/button.svelte";

    let isOpen = $state(false);

    let {
        data,
    }: {
        data: {
            form: SuperValidated<Infer<SearchEstadosSchema>>;
            estadosPage: IPage<IEstado>;
        };
    } = $props();

    function limparCampos() {
        formData.update((data) => ({
            ...data,
            uf: "",
            populacaoIni: undefined,
            populacaoFim: undefined,
        }));

        const params = new URLSearchParams($page.url.search);
        params.delete("uf");
        params.delete("populacaoIni");
        params.delete("populacaoFim");
        params.set("page", "0");

        isOpen = false;

        goto(`?${params.toString()}`);
    }

    const form = superForm(data.form, {
        validators: zodClient(searchEstadosSchema),
        onUpdated: ({ form: f }) => {
            if (f.valid) {
                const params = new URLSearchParams($page.url.search);

                if (f.data.uf) {
                    params.set("uf", f.data.uf);
                } else {
                    params.delete("uf");
                }
                if (f.data.populacaoIni) {
                    params.set("populacaoIni", f.data.populacaoIni.toString());
                } else {
                    params.delete("populacaoIni");
                }
                if (f.data.populacaoFim) {
                    params.set("populacaoFim", f.data.populacaoFim.toString());
                } else {
                    params.delete("populacaoFim");
                }

                params.set("page", "0");

                isOpen = false;

                goto(`?${params.toString()}`);
            } else {
                if (f.errors?._errors?.length) {
                    toast.error(f.errors._errors[0]);
                } else if (Object.keys(f.errors).length > 0) {
                    toast.error("Por favor, verifique os campos destacados.");
                }
            }
        },
    });

    const { form: formData, enhance } = form;
</script>

<Sheet.Root open={isOpen} onOpenChange={(e: boolean) => (isOpen = e)}>
    <Sheet.Trigger class={buttonVariants({ variant: "default" })}
        >Pesquisar</Sheet.Trigger
    >
    <Sheet.Content side="right">
        <Sheet.Header>
            <Sheet.Title>Pesquisar Estados</Sheet.Title>
            <Sheet.Description>
                Filtre os estados para encontrar exatamente o que precisa.
            </Sheet.Description>
        </Sheet.Header>

        <form method="POST" use:enhance class="w-full px-4">
            <Form.Field {form} name="uf">
                <Form.Control>
                    {#snippet children({
                        props,
                    }: {
                        props: Record<string, any>;
                    })}
                        <Form.Label>UF</Form.Label>
                        <Select.Root
                            type="single"
                            bind:value={$formData.uf}
                            name={props.name}
                        >
                            <Select.Trigger {...props} class="w-full">
                                {$formData.uf
                                    ? $formData.uf
                                    : "Selecione um estado"}
                            </Select.Trigger>
                            <Select.Content>
                                <Select.Item value="">
                                    Selecione um estado
                                </Select.Item>
                                {#each ["AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"] as uf}
                                    <Select.Item value={uf}>{uf}</Select.Item>
                                {/each}
                            </Select.Content>
                        </Select.Root>
                    {/snippet}
                </Form.Control>
                <Form.FieldErrors />
            </Form.Field>
            <p class="mb-1">Intervalo de população</p>
            <div class="flex gap-4">
                <Form.Field {form} name="populacaoIni">
                    <Form.Control>
                        {#snippet children({
                            props,
                        }: {
                            props: Record<string, any>;
                        })}
                            <Input
                                type="number"
                                placeholder="De"
                                {...props}
                                bind:value={$formData.populacaoIni}
                            />
                        {/snippet}
                    </Form.Control>
                    <Form.FieldErrors />
                </Form.Field>

                <Form.Field {form} name="populacaoFim">
                    <Form.Control>
                        {#snippet children({
                            props,
                        }: {
                            props: Record<string, any>;
                        })}
                            <Input
                                type="number"
                                placeholder="Até"
                                {...props}
                                bind:value={$formData.populacaoFim}
                            />
                        {/snippet}
                    </Form.Control>
                    <Form.FieldErrors />
                </Form.Field>
            </div>
            <Button
                type="button"
                class="mt-2 w-full"
                onclick={limparCampos}
                variant="outline"
            >
                Limpar Campos</Button
            >

            <Form.Button class="w-full mt-2">Buscar</Form.Button>
        </form>
    </Sheet.Content>
</Sheet.Root>

<script lang="ts">
    import * as Sheet from "$lib/components/ui/sheet/index.js";
    import { buttonVariants } from "$lib/components/ui/button/index.js";
    import { Input } from "$lib/components/ui/input/index.js";
    import * as Form from "$lib/components/ui/form/index.js";
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

    let {
        data,
    }: {
        data: {
            form: SuperValidated<Infer<SearchEstadosSchema>>;
            estadosPage: IPage<IEstado>;
        };
    } = $props();

    const form = superForm(data.form, {
        validators: zodClient(searchEstadosSchema),
        onUpdated: ({ form: f }) => {
            if (f.valid) {
                toast.success(f.data);

                goto("/dashboard");
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

<Sheet.Root>
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
        <form method="POST" use:enhance>
            <Form.Field {form} name="uf">
                <Form.Control>
                    {#snippet children({
                        props,
                    }: {
                        props: Record<string, any>;
                    })}
                        <Form.Label>UF</Form.Label>
                        <Input
                            {...props}
                            bind:value={$formData.uf}
                            placeholder="Exemplo: DF"
                        />
                    {/snippet}
                </Form.Control>
                <Form.FieldErrors />
            </Form.Field>
            <Form.Field {form} name="populacaoIni">
                <Form.Control>
                    {#snippet children({
                        props,
                    }: {
                        props: Record<string, any>;
                    })}
                        <Form.Label>a</Form.Label>
                        <Input
                            placeholder="Insira sua senha"
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
                        <Form.Label>b</Form.Label>
                        <Input
                            placeholder="Insira sua senha"
                            {...props}
                            bind:value={$formData.populacaoFim}
                        />
                    {/snippet}
                </Form.Control>
                <Form.FieldErrors />
            </Form.Field>
            <Form.Button class="w-full mt-2">Entrar</Form.Button>
        </form>
        <Sheet.Footer>
            <Sheet.Close class={buttonVariants({ variant: "default" })}
                >Buscar</Sheet.Close
            >
        </Sheet.Footer>
    </Sheet.Content>
</Sheet.Root>

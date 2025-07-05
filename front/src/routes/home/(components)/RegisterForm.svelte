<script lang="ts">
    import * as Form from "$lib/components/ui/form/index.js";
    import { Input } from "$lib/components/ui/input/index.js";
    import {
        type SuperValidated,
        type Infer,
        superForm,
    } from "sveltekit-superforms";
    import { zodClient } from "sveltekit-superforms/adapters";
    import { toast } from "svelte-sonner";
    import {
        registerSchema,
        type RegisterSchema,
    } from "../(schema)/registerSchema";
    import Checkbox from "$lib/components/ui/checkbox/checkbox.svelte";
    import { USER_ROLE } from "$lib/enums/auth";

    let {
        data,
    }: {
        data: { registerForm: SuperValidated<Infer<RegisterSchema>> };
    } = $props();

    const form = superForm(data.registerForm, {
        validators: zodClient(registerSchema),
        onUpdated: ({ form: f }) => {
            if (f.valid) {
                toast.success(
                    "Cadastro realizado com sucesso! Para continuar, faça login.",
                );
                success = true;
            } else {
                if (f.errors?._errors?.length) {
                    toast.error(f.errors._errors[0]);
                } else if (Object.keys(f.errors).length > 0) {
                    toast.error("Por favor, verifique os campos destacados.");
                }
            }
        },
    });

    let success = $state<boolean>(false);

    const { form: formData, enhance } = form;
</script>

{#if !success}
    <form method="POST" use:enhance action="?/register">
        <Form.Field {form} name="email">
            <Form.Control>
                {#snippet children({ props })}
                    <Form.Label>Email</Form.Label>
                    <Input
                        {...props}
                        bind:value={$formData.email}
                        placeholder="exemplo@email.com"
                    />
                {/snippet}
            </Form.Control>
            <Form.FieldErrors />
        </Form.Field>
        <Form.Field {form} name="password">
            <Form.Control>
                {#snippet children({ props })}
                    <Form.Label>Password</Form.Label>
                    <Input
                        type="password"
                        placeholder="Insira sua senha"
                        {...props}
                        bind:value={$formData.password}
                    />
                {/snippet}
            </Form.Control>
            <Form.FieldErrors />
        </Form.Field>
        <Form.Field {form} name="confirmPassword">
            <Form.Control>
                {#snippet children({ props })}
                    <Form.Label>Confirmar Senha</Form.Label>
                    <Input
                        type="password"
                        placeholder="Confirme sua senha"
                        {...props}
                        bind:value={$formData.confirmPassword}
                    />
                {/snippet}
            </Form.Control>
            <Form.FieldErrors />
        </Form.Field>
        <Form.Field {form} name="role">
            <Form.Control>
                {#snippet children({ props })}
                    <div class="flex items-center justify-start gap-2">
                        <Checkbox
                            {...props}
                            checked={$formData.role === USER_ROLE.PROFESSOR}
                            value={$formData.role.toString()}
                            onCheckedChange={(v) => {
                                $formData.role = v
                                    ? USER_ROLE.PROFESSOR
                                    : USER_ROLE.ALUNO;
                            }}
                        />
                        <Form.Label>Professor</Form.Label>
                    </div>
                {/snippet}
            </Form.Control>
            <Form.FieldErrors />
        </Form.Field>
        <Form.Button class="w-full mt-2">Entrar</Form.Button>
    </form>
{/if}
{#if success}
    <div class="w-full flex items-center justify-center flex-col gap-2">
        <svg
            class="text-green-600"
            xmlns="http://www.w3.org/2000/svg"
            width="120"
            height="120"
            viewBox="0 0 24 24"
            ><path
                fill="currentColor"
                d="m10.6 13.8l-2.15-2.15q-.275-.275-.7-.275t-.7.275t-.275.7t.275.7L9.9 15.9q.3.3.7.3t.7-.3l5.65-5.65q.275-.275.275-.7t-.275-.7t-.7-.275t-.7.275zM12 22q-2.075 0-3.9-.788t-3.175-2.137T2.788 15.9T2 12t.788-3.9t2.137-3.175T8.1 2.788T12 2t3.9.788t3.175 2.137T21.213 8.1T22 12t-.788 3.9t-2.137 3.175t-3.175 2.138T12 22m0-2q3.35 0 5.675-2.325T20 12t-2.325-5.675T12 4T6.325 6.325T4 12t2.325 5.675T12 20m0-8"
            /></svg
        >
        <span class="text-green-600">Cadastro realizado com sucesso!</span>
    </div>
{/if}

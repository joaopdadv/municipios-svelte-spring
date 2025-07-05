<script lang="ts">
    import * as Form from "$lib/components/ui/form/index.js";
    import { Input } from "$lib/components/ui/input/index.js";
    import {
        type SuperValidated,
        type Infer,
        superForm,
    } from "sveltekit-superforms";
    import { zodClient } from "sveltekit-superforms/adapters";
    import { loginSchema, type LoginSchema } from "../(schema)/loginSchema";
    import { toast } from "svelte-sonner";
    import { goto } from "$app/navigation";

    let { data }: { data: { loginForm: SuperValidated<Infer<LoginSchema>> } } =
        $props();

    const form = superForm(data.loginForm, {
        validators: zodClient(loginSchema),
        onUpdated: ({ form: f }) => {
            if (f.valid) {
                toast.success(f.message || "Login realizado com sucesso!");
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

<form method="POST" use:enhance action="?/login">
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
    <Form.Button class="w-full mt-2">Entrar</Form.Button>
</form>

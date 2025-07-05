<script lang="ts">
    import * as Dialog from "$lib/components/ui/dialog/index.js";
    import { buttonVariants } from "$lib/components/ui/button/index.js"; // Ajuste o caminho se necessário
    import type { PageData } from "../$types"; // Ajuste o caminho se necessário

    import LoginForm from "./LoginForm.svelte";
    import RegisterForm from "./RegisterForm.svelte";

    type FormType = "login" | "register";

    let {
        initialForm = "login" as FormType,
        data,
    }: {
        initialForm?: FormType;
        data: PageData;
    } = $props();

    let activeForm = $state<FormType>(initialForm);

    const formConfig = {
        login: {
            component: LoginForm,
            triggerText: "Entrar",
            title: "Acessar Plataforma",
            description: "Entre com seu e-mail e senha para continuar.",
            switcherText: "Ainda não tem uma conta?",
            switcherLinkText: "Cadastre-se",
            switchTo: "register" as FormType,
        },
        register: {
            component: RegisterForm,
            triggerText: "Cadastrar",
            title: "Crie sua Conta",
            description: "Preencha os dados abaixo para criar seu acesso.",
            switcherText: "Já tem uma conta?",
            switcherLinkText: "Entre",
            switchTo: "login" as FormType,
        },
    };

    const currentConfig = $derived(formConfig[activeForm]); // $derived para garantir que a mudança de activeForm atualize currentConfig

    const triggerConfig = formConfig[initialForm]; // Não é derivado, pois é usado apenas no trigger e não muda dinamicamente

    function switchTo(form: FormType) {
        activeForm = form;
    }

    let open = $state<boolean>(false);

    function onOpenChange(isOpen: boolean) {
        if (isOpen) {
            activeForm = initialForm;
        }
        open = isOpen;
        return isOpen;
    }
</script>

<Dialog.Root {onOpenChange} {open}>
    <Dialog.Trigger class={buttonVariants({ variant: "default" })}>
        {triggerConfig.triggerText}
    </Dialog.Trigger>

    <Dialog.Content
        class="sm:max-w-[425px]"
        tabindex={-1}
        onOpenAutoFocus={(event) => {
            event.preventDefault();
        }}
    >
        <Dialog.Header>
            <Dialog.Title>{currentConfig.title}</Dialog.Title>
            <Dialog.Description>{currentConfig.description}</Dialog.Description>
        </Dialog.Header>

        {#key currentConfig.component}
            <currentConfig.component {data} />
        {/key}

        <p class="text-sm text-center text-muted-foreground pt-4">
            {currentConfig.switcherText}
            <button
                class="cursor-pointer hover:text-primary/70 text-primary transition font-semibold bg-transparent border-none p-0"
                onclick={() => switchTo(currentConfig.switchTo)}
            >
                {currentConfig.switcherLinkText}
            </button>
        </p>
    </Dialog.Content>
</Dialog.Root>

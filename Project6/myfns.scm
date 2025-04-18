
; A PLAN program has the form (planProg <expr>)
(define (plan program)
  (evalExpr (cadr program) '()))  ; Evaluate the expression part with an empty environment

; Evaluates a PLAN expression with the current env
; - expr: The expression to evaluate
; - env: The current environment (list of bindings)
; Returns the integer value of the expression
(define (evalExpr expr env)
  (cond
    ; If expr is an integer constant, return it directly
    ((integer? expr) expr)
    
    ; If expr is a symbol (identifier), look up its value in the env
    ((symbol? expr) (lookup expr env))
    
    ; If expr is a list, determine its type and evaluate 
    ((list? expr)
     (cond
       ; planIf: Conditional expression (planIf <condition> <then-expr> <else-expr>)
       ((equal? (car expr) 'planIf) (evalPlanIf expr env))
       
       ; planAdd: Addition expression (planAdd <expr1> <expr2>)
       ((equal? (car expr) 'planAdd) (evalPlanAdd expr env))
       
       ; planMul: Multiplication expression (planMul <expr1> <expr2>)
       ((equal? (car expr) 'planMul) (evalPlanMul expr env))
       
       ; planSub: Subtraction expression (planSub <expr1> <expr2>)
       ((equal? (car expr) 'planSub) (evalPlanSub expr env))
       
       ; planLet: Variable binding expression (planLet <id> <val-expr> <body-expr>)
       ((equal? (car expr) 'planLet) (evalPlanLet expr env))
       
       ; Function call: (<id> <expr>)
       (else (evalFunctionCall expr env))))))

; Evaluates a planIf expression: (planIf <condition> <then-expr> <else-expr>)
; If condition > 0, evaluates then-expr, otherwise evaluates else-expr
(define (evalPlanIf expr env)
  (if (> (evalExpr (cadr expr) env) 0)  ; Evaluate condition
      (evalExpr (caddr expr) env)       ; If true, evaluate then-expr
      (evalExpr (cadddr expr) env)))    ; If false, evaluate else-expr

; Evaluates a planAdd expression: (planAdd <expr1> <expr2>)
; Returns the sum of the two expressions
(define (evalPlanAdd expr env)
  (+ (evalExpr (cadr expr) env)    ; Evaluate first expr
     (evalExpr (caddr expr) env))) ; Evaluate second expr

; Evaluates a planMul expression: (planMul <expr1> <expr2>)
; Returns the product of the two expressions
(define (evalPlanMul expr env)
  (* (evalExpr (cadr expr) env)    ; Evaluate first expr
     (evalExpr (caddr expr) env))) ; Evaluate second expr

; Evaluates a planSub expression: (planSub <expr1> <expr2>)
; Returns the difference of the two expressions
(define (evalPlanSub expr env)
  (- (evalExpr (cadr expr) env)    ; Evaluate first expr
     (evalExpr (caddr expr) env))) ; Evaluate second expr

; Evaluates a planLet expression: (planLet <id> <val-expr> <body-expr>)
; Binds the identifier to the value and evaluates the body in the extended environment
(define (evalPlanLet expr env)
  (let ((id (cadr expr))          ; Get the identifier
        (val-expr (caddr expr)))  ; Get the value expression
    (if (and (list? val-expr) (equal? (car val-expr) 'planFunction))
        ; Case 1: val-expr is a function definition (planFunction <param> <body>)
        ; Bind the function definition directly to id without evaluating it
        (evalExpr (cadddr expr) (cons (cons id val-expr) env))
        
        ; Case 2: val-expr is a regular expression
        ; Evaluate val-expr and bind the result to id
        (evalExpr (cadddr expr) (cons (cons id (evalExpr val-expr env)) env)))))

; Evaluates a function call expression: (<id> <expr>)
; Looks up the function, evaluates the argument, and evaluates the function body
; with the parameter
(define (evalFunctionCall expr env)
  (let ((func-id (car expr))        ; Get the function identifier
        (arg-expr (cadr expr)))     ; Get the argument expression
    (let ((func-def (lookup func-id env)))  ; Look up the function definition
      (let ((param-id (cadr func-def))      ; Get the parameter identifier
            (body-expr (caddr func-def))    ; Get the function body
            (arg-val (evalExpr arg-expr env)))  ; Evaluate the argument
        ; Evaluate the function body with parameter bound to argument value
        (evalExpr body-expr (cons (cons param-id arg-val) env))))))

; Looks up an identifier in the environment
; Uses dynamic scoping
; - id: The identifier to look up
; - env: The environment
; Returns the value bound to the identifier
(define (lookup id env)
  (cond
    ; If the first binding's identifier matches, return its value
    ((equal? (caar env) id) (cdar env))
    ; Otherwise, recursively look in the rest of the environment
    (else (lookup id (cdr env)))))

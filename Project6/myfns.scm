; Main function that evaluates a PLAN program
(define (plan program)
  (evalExpr (cadr program) '()))

; Evaluates a PLAN expression with the current environment
(define (evalExpr expr env)
  (cond
    ; Case 1: Expression is an integer constant
    ((integer? expr) expr)
    
    ; Case 2: Expression is an identifier
    ((symbol? expr) (lookup expr env))
    
    ; Case 3: Expression is a list (compound expression)
    ((list? expr)
     (cond
       ; planIf expression
       ((equal? (car expr) 'planIf) (evalPlanIf expr env))
       
       ; planAdd expression
       ((equal? (car expr) 'planAdd) (evalPlanAdd expr env))
       
       ; planMul expression
       ((equal? (car expr) 'planMul) (evalPlanMul expr env))
       
       ; planSub expression
       ((equal? (car expr) 'planSub) (evalPlanSub expr env))
       
       ; planLet expression
       ((equal? (car expr) 'planLet) (evalPlanLet expr env))
       
       ; Function call expression (id expr)
       (else (evalFunctionCall expr env))))))

; Evaluates a planIf expression
(define (evalPlanIf expr env)
  (if (> (evalExpr (cadr expr) env) 0)
      (evalExpr (caddr expr) env)
      (evalExpr (cadddr expr) env)))

; Evaluates a planAdd expression
(define (evalPlanAdd expr env)
  (+ (evalExpr (cadr expr) env)
     (evalExpr (caddr expr) env)))

; Evaluates a planMul expression
(define (evalPlanMul expr env)
  (* (evalExpr (cadr expr) env)
     (evalExpr (caddr expr) env)))

; Evaluates a planSub expression
(define (evalPlanSub expr env)
  (- (evalExpr (cadr expr) env)
     (evalExpr (caddr expr) env)))

; Evaluates a planLet expression
(define (evalPlanLet expr env)
  (let ((id (cadr expr))
        (val-expr (caddr expr)))
    ; Check if val-expr is a planFunction
    (if (and (list? val-expr) (equal? (car val-expr) 'planFunction))
        ; Handle function definition
        (evalExpr (cadddr expr) (cons (cons id val-expr) env))
        ; Handle regular variable binding
        (evalExpr (cadddr expr) (cons (cons id (evalExpr val-expr env)) env)))))

; Evaluates a function call expression
(define (evalFunctionCall expr env)
  (let ((func-id (car expr))
        (arg-expr (cadr expr)))
    (let ((func-def (lookup func-id env)))
      (if (and (list? func-def) (equal? (car func-def) 'planFunction))
          (let ((param-id (cadr func-def))
                (body-expr (caddr func-def))
                (arg-val (evalExpr arg-expr env)))
            ; Create a new environment with the parameter binding
            (evalExpr body-expr (cons (cons param-id arg-val) env)))
          0)))) ; Should not happen with valid input

; Looks up an identifier in the environment
; Uses dynamic scoping (most recent binding)
(define (lookup id env)
  (cond
    ((equal? (caar env) id) (cdar env))
    (else (lookup id (cdr env)))))

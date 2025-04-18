; Main function that evaluates a PLAN program
(define (plan program)
  (evalExpr (cadr program) '()))

; Evaluates a PLAN expression with the current environment
(define (evalExpr expr env)
  (cond
    ((integer? expr) expr)
    ((symbol? expr) (lookup expr env))
    ((list? expr)
     (cond
       ((equal? (car expr) 'planIf) (evalPlanIf expr env))
       ((equal? (car expr) 'planAdd) (evalPlanAdd expr env))
       ((equal? (car expr) 'planMul) (evalPlanMul expr env))
       ((equal? (car expr) 'planSub) (evalPlanSub expr env))
       ((equal? (car expr) 'planLet) (evalPlanLet expr env))
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
    (if (and (list? val-expr) (equal? (car val-expr) 'planFunction))
        (evalExpr (cadddr expr) (cons (cons id val-expr) env))
        (evalExpr (cadddr expr) (cons (cons id (evalExpr val-expr env)) env)))))

; Evaluates a function call expression
(define (evalFunctionCall expr env)
  (let ((func-id (car expr))
        (arg-expr (cadr expr)))
    (let ((func-def (lookup func-id env)))
      (let ((param-id (cadr func-def))
            (body-expr (caddr func-def))
            (arg-val (evalExpr arg-expr env)))
        (evalExpr body-expr (cons (cons param-id arg-val) env))))))

; Looks up an identifier in the environment
(define (lookup id env)
  (cond
    ((equal? (caar env) id) (cdar env))
    (else (lookup id (cdr env)))))

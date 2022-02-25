from CadenaDeMarkov import CadenaDeMarkov

if __name__ == '__main__':
    # Ejemplo tomado de la página https://www.gestiondeoperaciones.net/cadenas-de-markov/cadenas-de-markov-ejercicios-resueltos/.
    
    m_probabilidades = [[0.8,  0.1,  0.1], 
                        [0.03, 0.95, 0.02],
                        [0.2,  0.05, 0.75]]
    f0 = [0.45, 0.25, 0.3]
    cm = CadenaDeMarkov(['1', '2', '3'], f0, m_probabilidades)

    print(f'Secuencia en un paso: f^1 = {cm.generar_secuencia(1)}')
    print(f'Probabilidad de 1 en t = 3: {cm.obtener_probabilidad([str(1)], 3)}')
    print(f'Distrubición límite: {cm.calcular_distribucion_limite()}')


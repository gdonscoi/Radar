##Radares Móveis Araras

Aplicativo voltado para facilitar a localização dos radares móveis de Araras. Podendo evitar que você tome uma multa.

Pode ser adaptado para outras cidades, a partir do momento em que você alimentar os arquivos: Radar e RadarLocal.<br>

####RadarLocal:<br>
Contem o "cadastro" das ruas e suas respectivas informações. Ex:
```
{
  "id": "1",                                                
  "nomeRua": "Av. Loreto",                                  
  "localizacaoInicio": "-22.360964, -47.371664",            
  "localizacaoFim": "-22.351872, -47.347494",               
  "descricao": "limite 40 km/h",                            
  "pontos": "|jngC|gc`H?CCGCICMCKCQAa@CoA?[AgBAmACaA?W?QBA@ABC@A@A@C@C@C@A?C?C@A?CAC?C?AAC?CAAACAAAAAAAACAAACACq@Cs@A_AC_AA_AA_AG{BA_AA_AE_AC}@E}@C}@?OEm@?ECg@C]Eg@AQAWA]A_@Ag@Cq@Ek@AUEiACg@A[?GEw@E}@IwB?AGwBM}BCe@GsAIsCE_ACm@@e@B[@M?M?IAMMs@Qa@Yk@IQCKCMAGAGAGAG?GAK?GAG?K?O??@E?C?C?A?C?A?A?CAA?CAA?AACAAAAAAEECAEAC?CAE?C?C@C?A@A?A@A?A@C@A@I?C?C?C?AAC?C?CAA?CAWGYI_@Ow@YGCICGEGCGCGEICGEGEGEEEGEGEGEGEEGGEEGGEEGEEGGEGEGa@m@sAqBEM?A?A?A?A?A?A?AAA?AA??AA??AA??AA?AAA?A?A?SWm@_Am@aAi@w@i@y@_@c@_@e@@E?E?EACAEAEACACCECACCCAECC?EAC?EMEKEMEKEIGMCIIMGMGMIMGKGGCCIE?AAA?A?A?AAAAA?AAA?AA?AAAAAAA?AAA?A?AAEKEGGI[SWQYWk@o@EEEEEEEGGEEEIGkBkB_C_CECEEGECAIGGEECECEAWW"  
}
```
O que significa?<br>
* id: Identificador da rua<br>
* noemRua: Nome da rua<br>
* localizacaoInicio: Ponto inicial que será marcado no mapa<br>
* localizacaoFim: Ponto final que será marcado no mapa<br>
* descricao: Descrição da velocidade permitida<br>
* pontos: Pontos geográficos com ecoding do Google. https://github.com/googlemaps/android-maps-utils<br>

####Radar:<br>
Contem os dias da semana relacionados com os radares que irão ter. Ex:
```
{
  "semana": "0",
  "data": "28/09/2014",
  "radarLocal": ["1","4","10","21","18","19","3","17"]
}
```
O que significa?<br>
* semana: Identificador da semana começando do domingo<br>
* data: Data do dia<br>
* radarLocal: Identificador do "cadastro" de radares (RadarLocal)


[Link Divulgação dos radares](http://www.araras.sp.gov.br/e/?c=especial&i=12412)<br>
[Link para download no Google Play](https://play.google.com/store/apps/details?id=br.com.radaresmoveisararas)

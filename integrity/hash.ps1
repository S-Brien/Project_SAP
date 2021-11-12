$filename = Read-Host -Prompt 'input file to hash'
Get-FileHash $filename | Format-List